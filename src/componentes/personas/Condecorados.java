package componentes.personas;

public class Condecorados {
    private String lastName;
    private String firstName;
    private String officerOrEnlistedIndividual;
    private String typeOfActionCommendedByOriginator;
    private String nameOfApprovedAward;
    public Condecorados(String lastName, String firstName, String officerOrEnlistedIndividual, String typeOfActionCommendedByOriginator, String nameOfApprovedAward) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.officerOrEnlistedIndividual = officerOrEnlistedIndividual;
        this.typeOfActionCommendedByOriginator = typeOfActionCommendedByOriginator;
        this.nameOfApprovedAward = nameOfApprovedAward;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOfficerOrEnlistedIndividual() {
        return officerOrEnlistedIndividual;
    }

    public void setOfficerOrEnlistedIndividual(String officerOrEnlistedIndividual) {
        this.officerOrEnlistedIndividual = officerOrEnlistedIndividual;
    }

    public String getTypeOfActionCommendedByOriginator() {
        return typeOfActionCommendedByOriginator;
    }

    public void setTypeOfActionCommendedByOriginator(String typeOfActionCommendedByOriginator) {
        this.typeOfActionCommendedByOriginator = typeOfActionCommendedByOriginator;
    }

    public String getNameOfApprovedAward() {
        return nameOfApprovedAward;
    }

    public void setNameOfApprovedAward(String nameOfApprovedAward) {
        this.nameOfApprovedAward = nameOfApprovedAward;
    }

    @Override
    public String toString() {
        return "Condecorados{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", officerOrEnlistedIndividual='" + officerOrEnlistedIndividual + '\'' +
                ", typeOfActionCommendedByOriginator='" + typeOfActionCommendedByOriginator + '\'' +
                ", nameOfApprovedAward=" + nameOfApprovedAward +
                '}';
    }
}
