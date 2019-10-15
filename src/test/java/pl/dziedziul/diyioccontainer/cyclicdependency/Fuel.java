package pl.dziedziul.diyioccontainer.cyclicdependency;

class Fuel {
    private final Car car;

    Fuel(final Car car) {
        this.car = car;
    }
}
