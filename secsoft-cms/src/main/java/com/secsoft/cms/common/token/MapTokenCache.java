package com.secsoft.cms.common.token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.token.ITokenCache;
import com.jfinal.token.Token;

/**
 * 基于Hash的Token缓存
 *
 * @author luhf
 * @since 2019/07/28 16:15
 */
public class MapTokenCache implements ITokenCache {

    private Map<String, Token> tokenMap = new HashMap<>();

    @Override
    public void put(Token token) {
        tokenMap.put(token.getId(), token);
    }

    @Override
    public void remove(Token token) {
        tokenMap.remove(token.getId());
    }

    @Override
    public boolean contains(Token token) {
        return tokenMap.containsKey(token.getId());
    }

    @Override
    public List<Token> getAll() {
        return new ArrayList<>(tokenMap.values());
    }
}
