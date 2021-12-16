package uz.bots.service;

import uz.bots.Ticket;
import uz.bots.model.User;

import java.util.Date;

/**
 * Author: Muhammad
 * Date: 09.12.2021
 * Time: 19:27
 */
public class LoopWriter extends Thread {
    public LoopWriter() {
        start();
    }

    @Override
    public void run() {
        super.run();
        poolMethod();
    }

    private void poolMethod() {
//        List<User> users = new ArrayList<>();
//        ResultSet set = Ticket.statement.executeQuery("SELECT * FROM orders");
//        while (set.next()){
//            User user = new User();
//            user.setTgId(set.getLong(0));
//            user.setTgName(set.getString(1));
//            user.setUserName(set.getString(3));
//            user.setBiletName(set.getString(5));
//            user.setDatePart(set.getLong(7));
//            user.setDate(set.getLong(6));
//            user.setTelephoneNumber(set.getString(4));
//            users.add(user);
//        }
        try {
            String query = "INSERT INTO orders (tg_name,tg_id,user_name,telephone_number,bilet_name,sana,date_part) VALUES ";
            boolean write = false;
            for (Long id : TelegramBot.users.keySet()) {
                User user = TelegramBot.users.get(id);
                if (!user.getIsWrite()) {
                    if (user.getCompleted()) {
                        TelegramBot.users.get(id).setIsWrite(true);
                        query += "('" + user.getTgName() + "'," + user.getTgId() + ",'" + user.getUserName() + "','" + user.getTelephoneNumber() + "','" + user.getBiletName() + "'," + new Date().getTime() + "," + user.getDatePart() + "),";
                        write = true;
                    }
                }
            }

            if (write) {
                Ticket.statement.execute(query.substring(0, query.length() - 1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            sleep(10000);
            poolMethod();
        } catch (Exception e) {
            poolMethod();
        }
    }
}
