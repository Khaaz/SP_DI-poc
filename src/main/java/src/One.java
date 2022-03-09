package src;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class One {
    @Inject
    public Two two;
}
