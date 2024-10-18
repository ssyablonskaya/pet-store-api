package org.example.petstore;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pet {

    private long id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Tag[] tags;
    private String status;

    @Override
    public String toString() {
        return "Pet {" +
                "\nid=" + id +
                ", \ncategory=" + category +
                ", \nname='" + name + "'" +
                ", \nphotoUrls=" + Arrays.toString(photoUrls) +
                ", \ntags=" + Arrays.toString(tags) +
                ", \nstatus='" + status + "'\n}";
    }

}
