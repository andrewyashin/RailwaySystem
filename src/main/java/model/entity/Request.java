package model.entity;

import dao.mysql.TypePlace;
import lombok.*;

/**
 * Entity to table <b>REQUEST</b>
 *
 * @author Andrii Yashin
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request {
    private Long id;
    private Long userId;
    private Long trainId;

    private TypePlace type;

    private Double price;


    public static class RequestBuilder{
        private Request request;

        public RequestBuilder(){
            request = new Request();
        }

        public RequestBuilder setUserId(Long id){
            request.setUserId(id);
            return this;
        }

        public RequestBuilder setTrainId(Long id){
            request.setTrainId(id);
            return this;
        }

        public RequestBuilder setPrice(Double price){
            request.setPrice(price);
            return this;
        }

        public RequestBuilder setType(TypePlace type){
            request.setType(type);
            return this;
        }

        public Request build(){
            return request;
        }
    }
}
