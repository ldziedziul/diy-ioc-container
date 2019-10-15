package pl.dziedziul.diyioccontainer.beannotfound;

class SomeService {
    private final ImNotABean imNotABean;

    public SomeService(final ImNotABean imNotABean) {
        this.imNotABean = imNotABean;
    }
}
