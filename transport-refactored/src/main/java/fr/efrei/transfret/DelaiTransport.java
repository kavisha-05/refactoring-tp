package fr.efrei.transfret;

@FunctionalInterface
public interface DelaiTransport {
    int estimer(int distance, boolean urgent);
}
