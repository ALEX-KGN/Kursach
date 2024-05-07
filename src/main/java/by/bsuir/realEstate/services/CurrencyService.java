package by.bsuir.realEstate.services;

import by.bsuir.realEstate.utils.Rates;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CurrencyService {
    private final WebClient webClient;

    public CurrencyService(WebClient webClient) {
        this.webClient = webClient;
    }

    public double getCurrencies(){
        Rates rates = webClient
                .get()
                .retrieve()
                .bodyToMono(Rates.class)
                .block();
        double usd;
        if(rates.getRates().isEmpty()){
            usd = 3.0;
        }
        else{
            usd = rates.getRates().stream().filter(rate->rate.getIso().equals("USD")).findFirst().get().getRate();
        }
        return usd;
    }
}