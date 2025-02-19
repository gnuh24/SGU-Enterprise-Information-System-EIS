package BackEnd.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    private LocalDateTime activityTime;

    @ManyToOne
    @JoinColumn(name = "AccountId", nullable = false)
    private Account account;

    public enum ActivityStatus {
	ONLINE,
	OFFLINE
    }}

