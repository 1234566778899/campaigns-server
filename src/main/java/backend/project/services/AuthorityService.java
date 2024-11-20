package backend.project.services;

import backend.project.entities.Authority;

public interface AuthorityService {

    public Authority findByName(String name);
    public Authority findById(Long id);

    public Authority addAuthority(Authority authority);

}
