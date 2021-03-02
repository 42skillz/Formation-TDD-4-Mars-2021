package movierental;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CustomerShould
{
    @Test
    public final void print_statement_when_rent_a_regular_movie_for_3_days() {
        Customer vincent = new Customer("Vincent");
        vincent.addRental(new Rental(new Movie("Star wars", Movie.REGULAR), 3));
        assertThat(vincent.statement())
                .isEqualTo("Rental Record for Vincent\n" +
                        "\tStar wars\t3.5\n" +
                        "Amount owed is 3.5\n" +
                        "You earned 1 frequent renter points");
    }

    @Test
    public final void print_statement_when_rent_a_new_release_movie_for_4_days() {
        Customer vincent = new Customer("Vincent");
        vincent.addRental(new Rental(new Movie("No Time to Die", Movie.NEW_RELEASE), 4));
        assertThat(vincent.statement())
                .isEqualTo("Rental Record for Vincent\n" +
                        "\tNo Time to Die\t12.0\n" +
                        "Amount owed is 12.0\n" +
                        "You earned 2 frequent renter points");
    }

    @Test
    public final void print_statement_when_rent_a_children_movie_for_5_days() {
        Customer vincent = new Customer("Vincent");
        vincent.addRental(new Rental(new Movie("Frozen 2", Movie.CHILDREN), 5));
        assertThat(vincent.statement())
                .isEqualTo("Rental Record for Vincent\n" +
                        "\tFrozen 2\t4.5\n" +
                        "Amount owed is 4.5\n" +
                        "You earned 1 frequent renter points");
    }


    @Test
    public final void print_statement_when_rent_several_movies() {
        Customer vincent = new Customer("Vincent");

        Movie star_wars = new Movie("Star wars", Movie.NEW_RELEASE);
        star_wars.setPriceCode(Movie.REGULAR);
        vincent.addRental(new Rental(star_wars, 1));
        vincent.addRental(new Rental(new Movie("No Time to Die", Movie.NEW_RELEASE), 2));
        vincent.addRental(new Rental(new Movie("Frozen 2", Movie.CHILDREN), 5));

        assertThat(vincent.statement())
                .isEqualTo("Rental Record for Vincent\n" +
                        "\tStar wars\t2.0\n" +
                        "\tNo Time to Die\t6.0\n" +
                        "\tFrozen 2\t4.5\n" +
                        "Amount owed is 12.5\n" +
                        "You earned 4 frequent renter points");
    }

}
