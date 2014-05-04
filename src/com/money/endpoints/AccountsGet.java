package com.money.endpoints;

import com.endpoints.Endpoint;
import com.money.model.Account;
import com.money.serializer.JsonSerializer;
import com.money.services.AccountsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by michal on 5/3/14.
 */
public class AccountsGet extends Endpoint {

    public AccountsGet(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/accounts";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public String process() {
        List<Account> accountList = AccountsService.INSTANCE.getAll();
        String returnValue = new JsonSerializer(getRequest(), getPathData())
                .serialize(accountList).toString();
        return returnValue;
    }
}
