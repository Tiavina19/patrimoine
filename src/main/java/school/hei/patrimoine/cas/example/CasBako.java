package school.hei.patrimoine.cas.example;

import static java.time.Month.*;
import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

import java.time.LocalDate;
import java.util.Set;

import school.hei.patrimoine.cas.Cas;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

public class CasBako extends Cas {

    private static final LocalDate DATE_DE_BASE = LocalDate.of(2025, APRIL, 8);
    private static final LocalDate PROJECTION_FIN = LocalDate.of(2025, DECEMBER, 31);


    private static final int NOMBRE_FLUX = 8;

    private final Compte bni;
    private final Compte bmoi;
    private final Compte coffre;

    public CasBako() {
        super(PROJECTION_FIN, LocalDate.MAX, new Personne("Bako"));

        bni = new Compte("BNI - Compte courant", LocalDate.MIN, ariary(2_000_000));
        bmoi = new Compte("BMOI - Épargne", LocalDate.MIN, ariary(625_000));
        coffre = new Compte("Coffre à la maison", LocalDate.MIN, ariary(1_750_000));
    }

    @Override
    protected String nom() {
        return "Bako";
    }

    @Override
    protected Devise devise() {
        return MGA;
    }

    @Override
    protected void init() {}

    @Override
    public Set<Possession> possessions() {

        LocalDate fluxDebut = LocalDate.of(2025, MAY, 1);

        FluxArgent salaire = new FluxArgent(
                "Salaire mensuel",
                bni,
                LocalDate.of(2025, MAY, 2), // Premier salaire le 2 mai
                PROJECTION_FIN,
                NOMBRE_FLUX,
                ariary(2_125_000)
        );

        FluxArgent virementSortant = new FluxArgent(
                "Virement sortant vers BMOI",
                bni,
                LocalDate.of(2025, MAY, 3), // Premier virement le 3 mai
                PROJECTION_FIN,
                NOMBRE_FLUX,
                ariary(-200_000)
        );

        FluxArgent virementEntrant = new FluxArgent(
                "Virement reçu depuis BNI",
                bmoi,
                LocalDate.of(2025, MAY, 3),
                PROJECTION_FIN,
                NOMBRE_FLUX,
                ariary(200_000)
        );

        FluxArgent loyer = new FluxArgent(
                "Loyer colocation",
                bni,
                LocalDate.of(2025, MAY, 26),
                PROJECTION_FIN,
                NOMBRE_FLUX,
                ariary(-600_000)
        );

        FluxArgent depensesVie = new FluxArgent(
                "Dépenses de vie",
                bni,
                LocalDate.of(2025, MAY, 1),
                PROJECTION_FIN,
                NOMBRE_FLUX,
                ariary(-700_000)
        );


        Materiel ordinateur = new Materiel(
                "Ordinateur portable",
                DATE_DE_BASE,
                PROJECTION_FIN,
                ariary(3_000_000),
                -0.12 * (8.73 / 12)
        );

        return Set.of(
                bni, bmoi, coffre,
                salaire, virementSortant, virementEntrant,
                loyer, depensesVie,
                ordinateur
        );
    }

    @Override
    protected void suivi() {}
}