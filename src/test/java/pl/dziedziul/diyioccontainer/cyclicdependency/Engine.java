package pl.dziedziul.diyioccontainer.cyclicdependency;

class Engine {
    private final Fuel fuel;

    Engine(final Fuel fuel) {
        this.fuel = fuel;
    }
}
