package br.com.fgr.emergencia;

import org.junit.Test;

import static br.com.fgr.emergencia.utils.Helper.validateEmail;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class HelperTest {

    @Test
    public void validarEmailCorreto() {

        String email = "felipegr6@gmail.com";

        assertTrue(validateEmail(email));

    }

    @Test
    public void invalidarEmailIncorreto() {

        String email = "f@f";

        assertFalse(validateEmail(email));

    }

    @Test
    public void invalidarEmailQuaseCorreto() {

        String email = "f@f.c";

        assertFalse(validateEmail(email));

    }

}
