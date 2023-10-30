package TphonesShop.service;

import TphonesShop.model.Token;

public interface TokenService {
    public void cleanExpiredTokens();

    public void save(Token token);

    public void delete(Token token);

    public Token findByToken(String token);
}
