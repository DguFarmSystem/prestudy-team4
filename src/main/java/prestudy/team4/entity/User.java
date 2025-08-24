import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", length = 100, nullable = false, unique = true)
    private String userId;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "user_nn", length = 100, nullable = false, unique = true)
    private String userNn;

    public User(String userId, String password, String email, String userNn) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userNn = userNn;
    }
}
