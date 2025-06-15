package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Admin;
import org.springframework.data.repository.ListCrudRepository;

public interface IAdminDAO extends ListCrudRepository<Admin, Long> {
}
