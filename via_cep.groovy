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
        // Adicionando o campo Data no Json
        def date = new Date()
        jsonObject.Data = date.format("dd-MM-yyyy HH:mm:ss", timezone=tz)

        // Removendo dados 
        jsonObject.remove("gia")
        jsonObject.remove("ibge")
        jsonObject.remove("siafi")   

        // Adicionando um novo campo chamado Rua no lugar de Logradouro e removendo o Logradouro
        jsonObject.Rua = jsonObject.logradouro
        jsonObject.remove("logradouro")
        
        // New Json
        def  jsonNew  = JsonOutput.toJson(
            zip: jsonObject.cep,
            complement: jsonObject.complemento,
            neighborhood: jsonObject.bairro,
            locality: jsonObject.localidade,
            uf: jsonObject.uf,
            ddd: jsonObject.ddd,
            Date: jsonObject.Data,
            Street: jsonObject.Rua
            
        )

        // Acrescentando as mundaças no JsonObject
        // def json = new JsonBuilder(jsonObject).toPrettyString();
        message.setBody(JsonOutput.prettyPrint(jsonNew))
        
    return message;
    }
 