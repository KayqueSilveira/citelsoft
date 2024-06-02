package br.com.citelsoft.repository;

import br.com.citelsoft.model.Candidato;
import br.com.citelsoft.model.CandidatoDTO;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    @Query("SELECT c.estado, COUNT(c) FROM Candidato c GROUP BY c.estado")
    List<Object[]> countCandidatesByState();

    @Query(value = "SELECT tipo_sanguineo, " +
            "       COUNT(*) AS num_pessoas, " +
            "       ROUND(SUM(YEAR(CURDATE()) - YEAR(data_nasc) - (RIGHT(CURDATE(), 5) < RIGHT(data_nasc, 5))) / COUNT(*)) AS media_idade " +
            "FROM candidato " +
            "GROUP BY tipo_sanguineo", nativeQuery = true)
    List<Object[]> getAverageAgeByBloodType();

    @Query(value = "SELECT sexo, " +
            "SUM(CASE WHEN (peso / (altura * altura)) >= 30 THEN 1 ELSE 0 END) AS obesos, " +
            "COUNT(*) AS total, " +
            "(SUM(CASE WHEN (peso / (altura * altura)) >= 30 THEN 1 ELSE 0 END) / COUNT(*)) * 100 AS percentual_obesos " +
            "FROM Candidato " +
            "GROUP BY sexo", nativeQuery = true)
    List<Object[]> getObesePercentageBySex();

    @Query(value = "SELECT " +
            "receptor.tipo_sanguineo AS tipo_sanguineo_receptor, " +
            "GROUP_CONCAT(DISTINCT doador.tipo_sanguineo ORDER BY doador.tipo_sanguineo SEPARATOR ',') AS tipos_sanguineos_doadores, " +
            "COUNT(DISTINCT id) AS quantidade_doadores " +
            "FROM (SELECT DISTINCT c.tipo_sanguineo AS tipo_sanguineo FROM Candidato c) receptor " +
            "CROSS JOIN (SELECT DISTINCT c.tipo_sanguineo AS tipo_sanguineo FROM Candidato c) doador " +
            "LEFT JOIN Candidato c ON c.tipo_sanguineo = doador.tipo_sanguineo " +
            "WHERE " +
            "(receptor.tipo_sanguineo = 'A-' AND doador.tipo_sanguineo IN ('A-', 'O-')) OR " +
            "(receptor.tipo_sanguineo = 'A+' AND doador.tipo_sanguineo IN ('A+', 'A-', 'O+', 'O-')) OR " +
            "(receptor.tipo_sanguineo = 'B-' AND doador.tipo_sanguineo IN ('B-', 'O-')) OR " +
            "(receptor.tipo_sanguineo = 'B+' AND doador.tipo_sanguineo IN ('B+', 'B-', 'O+', 'O-')) OR " +
            "(receptor.tipo_sanguineo = 'AB-' AND doador.tipo_sanguineo IN ('A-', 'B-', 'AB-')) OR " +
            "(receptor.tipo_sanguineo = 'AB+' AND doador.tipo_sanguineo IN ('A+', 'O+', 'AB+', 'A-', 'B-', 'O-', 'AB-')) OR " +
            "(receptor.tipo_sanguineo = 'O-' AND doador.tipo_sanguineo IN ('O-')) OR " +
            "(receptor.tipo_sanguineo = 'O+' AND doador.tipo_sanguineo IN ('O+', 'O-')) " +
            "GROUP BY receptor.tipo_sanguineo " +
            "ORDER BY receptor.tipo_sanguineo", nativeQuery = true)
    List<Object[]> findDonorsByBloodType();

    Optional<Candidato> findByCpf(String cpf);

}
