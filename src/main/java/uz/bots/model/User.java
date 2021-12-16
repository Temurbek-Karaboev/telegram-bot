package uz.bots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Muhammad
 * Date: 07.12.2021
 * Time: 19:40
 */
@NoArgsConstructor
@Data
public class User {
   private Long id;
   private String tgName;
   private Long tgId;
   private String userName;
   private String telephoneNumber;
   private String biletName;
   private Long date;
   private Long datePart;

   private Boolean isWrite = false;
   private Boolean completed = false;
   private String step;

}
