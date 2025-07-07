package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Offer;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.domain.enums.OfferStatus;
import br.ufscar.dc.dsw.email.IEmailService;
import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.service.spec.IClientService;
import br.ufscar.dc.dsw.service.spec.IOfferService;
import br.ufscar.dc.dsw.service.spec.IVehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OfferController {

    @Autowired
    private IOfferService offerService;

    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IClientService clientService;

    @GetMapping("/vehicle/{vehicleId}/offer/register")
    public String register(@PathVariable Long vehicleId, ModelMap model) {
        model.addAttribute("vehicle", vehicleService.findById(vehicleId));
        model.addAttribute("offer", new Offer());
        return "vehicle/offer";
    }

    @PostMapping("/vehicle/{vehicleId}/offer/save")
    public String save(@PathVariable Long vehicleId, @Valid Offer offer, BindingResult result,
                       RedirectAttributes attributes, ModelMap model, Principal principal) {
        Vehicle vehicle = vehicleService.findById(vehicleId);
        offer.setVehicle(vehicle);

        if (result.hasErrors()) {
            model.addAttribute("vehicle", vehicle);
            return "vehicle/offer";
        }

        try {
            offer.setClient(clientService.findByEmail(principal.getName()));
            offerService.save(offer);
            attributes.addFlashAttribute("success", "offer.create.success");
            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("fail", "Error creating an offer!");
            return "vehicle/offer";
        }
    }

    @GetMapping("/offer/analyze/{id}")
    public String analyzeOffer(@PathVariable("id") Long id, ModelMap model) {
        Offer offer = offerService.findById(id);

        if (offer == null) {
            throw new ResourceNotFoundException("Oferta não encontrada");
        }

        model.addAttribute("offer", offer);
        return "store/offerAnalysis";
    }

    @PostMapping("/offer/decision/{id}")
    public String processDecision(
            @PathVariable("id") Long id,
            @RequestParam("decision") String decision,
            @RequestParam(value = "meetingLink", required = false) String meetingLink,
            @RequestParam(value = "meetingDateTime", required = false) String meetingDateTime,
            @RequestParam(value = "counterValue", required = false) Double counterValue,
            @RequestParam(value = "counterConditions", required = false) String counterConditions,
            RedirectAttributes attributes) {

        Offer offer = offerService.findById(id);

        if (offer == null) {
            throw new ResourceNotFoundException("Oferta não encontrada");
        }

        try {
            // Atualizar status da oferta
            if ("ACCEPTED".equals(decision)) {
                offer.setStatus(OfferStatus.ACCEPTED);

                // Validar dados da reunião
                if (meetingLink == null || meetingLink.trim().isEmpty() ||
                        meetingDateTime == null || meetingDateTime.trim().isEmpty()) {
                    attributes.addFlashAttribute("fail",
                            "Para aceitar uma proposta, é necessário informar o link e data/hora da reunião.");
                    return "redirect:/offer/analyze/" + id;
                }

                // Atualizar status das outras ofertas abertas para rejeitá-las
                List<Offer> offers = offerService.findAllByVehicleId(offer.getVehicle().getId());
                for (Offer o : offers) {
                    if (o.getId().equals(offer.getId())) {
                        continue;
                    }

                    if (o.getStatus().equals(OfferStatus.REJECTED)){
                        continue;
                    }

                    if (o.getStatus().equals(OfferStatus.OPEN)) {
                        o.setStatus(OfferStatus.REJECTED);
                        offerService.update(o);
                    }
                }

                // Enviar email de aceitação
                sendAcceptanceEmail(offer, meetingLink, meetingDateTime);
            } else if ("REJECTED".equals(decision)) {
                offer.setStatus(OfferStatus.REJECTED);

                // Enviar email de rejeição (com contraproposta se houver)
                sendRejectionEmail(offer, counterValue, counterConditions);
            }

            // Salvar oferta atualizada
            offerService.update(offer);

            attributes.addFlashAttribute("success", "offer.decision.success");
            return "redirect:/store/offers";

        } catch (Exception e) {
            attributes.addFlashAttribute("fail", "offer.decision.fail");
            return "redirect:/offer/analyze/" + id;
        }
    }

    private void sendAcceptanceEmail(Offer offer, String meetingLink, String meetingDateTime) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("offer", offer);
        variables.put("meetingLink", meetingLink);
        variables.put("meetingDateTime", meetingDateTime);

        emailService.sendEmail(
                offer.getClient().getEmail(),
                "Sua proposta para o veículo " + offer.getVehicle().getModel() + " foi ACEITA!",
                "email/offer-accepted",
                variables
        );
    }

    private void sendRejectionEmail(Offer offer, Double counterValue, String counterConditions) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("offer", offer);

        // Adicionar informações de contraproposta, se houver
        boolean hasCounterOffer = counterValue != null && counterValue > 0;
        variables.put("hasCounterOffer", hasCounterOffer);

        if (hasCounterOffer) {
            variables.put("counterValue", counterValue);
            variables.put("counterConditions", counterConditions);
        }

        emailService.sendEmail(
                offer.getClient().getEmail(),
                "Resposta sobre sua proposta para o veículo " + offer.getVehicle().getModel(),
                "email/offer-rejected",
                variables
        );
    }
}
