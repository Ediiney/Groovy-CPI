import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.util.Date
import groovy.json.JsonBuilder
import groovy.json.*;
    
    def Message processData(Message message) {

        tz = TimeZone.getTimeZone("America/Sao_Paulo")

        // Declarações
        def body = message.getBody(String)
        def jsonParser = new JsonSlurper()
        def jsonObject = jsonParser.parseText(body)
        def builder = new JsonBuilder(jsonObject)

        // Pegando data no Horário Local
        def date = new Date()
        def dt = date.format("dd-MM-yyyy HH:mm:ss", timezone=tz)

        // Removendo dados 
        jsonObject.remove("gia")
        jsonObject.remove("ibge")
        jsonObject.remove("siafi")
        jsonObject.remove("dt")

        // Adicionando o campo Data no Json
        jsonObject.put("Data", dt)

        // Adicionando um novo campo chamado Rua no lugar de Logradouro e removendo o Logradouro
        jsonObject.Rua = jsonObject.logradouro
        jsonObject.remove("logradouro")

        // Acrescentando as mundaças no JsonObject
        def json = new JsonBuilder(jsonObject).toPrettyString();
        message.setBody(json);      
        
             
        return message;
    }
 
