package modeles;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.Date;
import utilitaire.Util;

public class BaseModele {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String buildQueryFindWhere() throws Exception {
        String res = this.getClass().getSimpleName() + " WHERE 1=1 ";
        Util util = new Util();
        Field[] champs = util.getFields(this.getClass());
        String operateur = " = ";
        String likePourcentage = "";
        String upper = "";
        for (int i = 0; i < champs.length; i++) {
            Method met = this.getClass().getMethod("get" + util.premierMaj(champs[i].getName()), null);
            Object obj = met.invoke(this, null);
            operateur = " = ";
            likePourcentage = "";
            upper = "";
            if (obj != null && obj.toString().compareToIgnoreCase("") != 0) {
                String val = obj.toString();
                switch (obj.getClass().getName()) {
                    case ("java.util.Date"):
                        val = util.dateToString((Date) obj);
                        break;
                    case ("java.lang.String"):
                        operateur = " = ";
                        likePourcentage = "%";
                        val = val.toUpperCase();
                        upper = "upper";

                }

                res = res + " AND " + upper + "(" + champs[i].getName().toUpperCase() + ")" + operateur + " '" + val + "'";
            }
        }
        
        return res;
    }

    public String buildQueryFind() throws Exception {
        String res = this.getClass().getSimpleName() + " WHERE 1=1 ";
        Util util = new Util();
        Field[] champs = util.getFields(this.getClass());
        String operateur = " = ";
        String likePourcentage = "";
        String upper = "";
        for (int i = 0; i < champs.length; i++) {
            Method met = this.getClass().getMethod("get" + util.premierMaj(champs[i].getName()), null);
            Object obj = met.invoke(this, null);
            operateur = " = ";
            likePourcentage = "";
            upper = "";
            if (obj != null && obj.toString().compareToIgnoreCase("") != 0) {
                String val = obj.toString();
                switch (obj.getClass().getName()) {
                    case ("java.util.Date"):
                        val = util.dateToString((Date) obj);
                        break;
                    case ("java.lang.String"):
                        operateur = " like ";
                        likePourcentage = "%";
                        val = val.toUpperCase();
                        upper = "upper";

                }

                res = res + " AND " + upper + "(" + champs[i].getName().toUpperCase() + ")" + operateur + " '" + likePourcentage + val + likePourcentage + "'";
            }
        }

        return res;
    }

    public String getValueAttrs(String[] n, String separateur) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String res = "";
        Util util = new Util();
        for (int i = 0; i < n.length; i++) {
            String sep = "";
            if (i != 0) {
                sep = separateur;
            }
            Method meth = this.getClass().getMethod("get" + util.premierMaj(n[i]), null);
            res = res + sep + meth.invoke(this, null).toString();
        }
        return res;
    }

    public void initValues() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Util util = new Util();
        Field[] champs = util.getFields(this.getClass());

        for (int i = 0; i < champs.length; i++) {
            Method met = this.getClass().getMethod("get" + util.premierMaj(champs[i].getName()), null);
            Object obj = met.invoke(this, null);

            if (obj == null) {
                Class[] type = new Class[1];
                type[0] = champs[i].getType();
                Method metSet = this.getClass().getMethod("set" + util.premierMaj(champs[i].getName()), type);
                Object param = null;

                if (champs[i].getType().getSimpleName().compareToIgnoreCase("Integer") == 0) {
                    param = new Integer(0);
                } else if (champs[i].getType().getSimpleName().compareToIgnoreCase("Boolean") == 0) {
                    param = new Boolean(false);
                } else if (champs[i].getType().getSimpleName().compareToIgnoreCase("Float") == 0) {
                    param = new Float(0.00);
                } else if (champs[i].getType().getSimpleName().compareToIgnoreCase("Time") == 0) {
                    param = new Time(0, 0, 0);
                } else {
                    param = champs[i].getType().newInstance();
                }
                metSet.invoke(this, param);

            }

        }
    }

}
