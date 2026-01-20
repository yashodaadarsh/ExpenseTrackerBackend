package com.adarsh.ExpenseService.Consumer;

import com.adarsh.ExpenseService.dto.ExpenseDto;
import com.adarsh.ExpenseService.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseConsumer {

    private ExpenseService expenseService;

    @Autowired
    ExpenseConsumer( ExpenseService expenseService ){
        this.expenseService = expenseService;
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDto expenseDto){
        try{
            //TODO : for Update operation -> fetch from SQL --> Update Logic --> save back to SQL . Add distributed locks using redis
            expenseService.createCurrency(expenseDto);
        }catch (Exception e){
            System.out.println("Exception in listening the event");
        }
    }


}
