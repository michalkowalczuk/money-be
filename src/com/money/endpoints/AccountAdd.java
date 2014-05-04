package com.money.endpoints;

import com.endpoints.Endpoint;
import com.money.model.Account;
import com.money.serializer.JsonSerializer;
import com.money.services.AccountsService;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by michal on 5/3/14.
 */
public class AccountAdd extends Endpoint {

    public AccountAdd(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/accounts";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public String process() {
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        AccountsService.INSTANCE.add(account);
        String returnValue = new JsonSerializer(getRequest(), getPathData())
                .serialize(account).toString();
        return returnValue;
    }
}
