package cn.ictt.zhanghui.eureka_provider.pojo;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 使用builder模式构建User
 * 
 * @author ZhangHui
 * @date 2019/6/21
 * @return
 */
@Data
@Entity
@Table(name = "tb_user")
@AllArgsConstructor
public class User implements Serializable {
    // required
    @Id
    private int userId;

    // required
    @Column
    private String userName;

    @Column
    private int userAge;

    // optional
    @Column
    private String description;

    @Column
    private int type;

    private User() {}

    public static class Builder {
        private int userId;
        private String userName;
        private int userAge;
        private String description;

        public Builder(int userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        public Builder builderAge(int userAge) {
            this.userAge = userAge;
            return this;
        }

        public Builder builderDesc(String description) {
            this.description = description;
            return this;
        }

        public User build() {
            User user = new User();
            user.userId = this.userId;
            user.userName = this.userName;
            user.userAge = this.userAge;
            user.description = this.description;
            return user;
        }
    }
}
