package com.project.ccts.model.enums;

import com.project.ccts.model.entities.HealthStatus;

import java.util.HashSet;
import java.util.Set;

/**
 * Recommendations based on health status
 * Reference: https://www.who.int/es/emergencies/diseases/novel-coronavirus-2019/advice-for-public
 *
 */
public enum Recommendations {
    FEVER(1, Set.of("Solicite atención médica", "Permanezca en casa")),
    COUGH(2, Set.of("Solicite atención médica", "Permanezca en casa")),
    BREATHDIFFICULTY(3, Set.of("Solicite atención médica", "Permanezca en casa")),
    SORETHROAT(4, Set.of("Sintomas leves", "Evite salir de casa")),
    SMELLLOSS(5, Set.of("Sintomas leves", "Evite salir de casa")),
    TASTELOSS(6, Set.of("Sintomas leves", "Evite salir de casa")),
    POSITIVE(7, Set.of("No salga de casa", "Mantengase en cuarentena", "Llame a su medico en caso de no poder respirar")),
    COMMON(8, Set.of(
            "Lávese las manos frecuentemente",
            "Adopte medidas de higiene respiratoria",
            "Mantenga el distanciamiento social",
            "Evite tocarse los ojos, la nariz y la boca",
            "Siga las recomendaciones del Ministerio de salud",
            "Utilice mascarilla y guantes",
            "Evite aglomeraciones",
            "Mantenga a CCTS informado de su estado de salud",
            "No auto-medicarse"
    ));

    private Integer id;
    private Set<String> recommendations;

    Recommendations(Integer id, Set<String> recommendations) {
        this.id = id;
        this.recommendations = recommendations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<String> recommendations) {
        this.recommendations = recommendations;
    }

    public static Set<String> getRecommendationsByHealthStatus(HealthStatus healthStatus) {
        Set<String> recommendations = new HashSet<>();
        recommendations.addAll(Recommendations.COMMON.getRecommendations());
        if(healthStatus.isFever()) {
            recommendations.addAll(Recommendations.FEVER.getRecommendations());
        }
        if(healthStatus.isTasteLoss()) {
            recommendations.addAll(Recommendations.TASTELOSS.getRecommendations());
        }
        if(healthStatus.isSmellLoss()) {
            recommendations.addAll(Recommendations.SMELLLOSS.getRecommendations());
        }
        if(healthStatus.isSoreThroat()) {
            recommendations.addAll(Recommendations.SORETHROAT.getRecommendations());
        }
        if(healthStatus.isBreathDifficulty()) {
            recommendations.addAll(Recommendations.BREATHDIFFICULTY.getRecommendations());
        }
        if(healthStatus.isCough()) {
            recommendations.addAll(Recommendations.COUGH.getRecommendations());
        }
        if (healthStatus.getTest() != null && healthStatus.getTest().getStatus()) {
            recommendations.addAll(Recommendations.POSITIVE.getRecommendations());
        }
        return recommendations;
    }
}
