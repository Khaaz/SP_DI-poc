package src;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class Two {
    @Inject
    public Three three;
}
