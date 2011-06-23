package org.obliquid.datatype;

/**
 * Hold and validate and normalize phone number
 * 
 * @author stivlo
 */
public class PhoneNumber extends DataType {

    private static final long serialVersionUID = 1L;

    public PhoneNumber() {
        super();
    }

    public PhoneNumber(String phoneString) throws IllegalArgumentException {
        set(phoneString);
    }

    @Override
    public boolean isValid(String phoneString) {
        String aPhone = normalize(phoneString);
        Character aChar;
        for (int i = 0; i < aPhone.length(); i++) {
            aChar = aPhone.charAt(i);
            if (i == 0 && aChar == '+') {
                continue;
            }
            if (aChar < '0' || aChar > '9') {
                if (aChar == '+' && i == 0) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void set(String data) throws IllegalArgumentException {
        if (!isValid(data)) {
            throw new IllegalArgumentException(getClass().getName() + " '" + data + "' is not valid");
        }
        this.data = normalize(data);
    }

    private String normalize(String phoneString) {
        phoneString = phoneString.trim().replaceAll(" ", "").replaceAll("/", "").replaceAll("\\.", "")
                .replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
        //System.out.println("phone: " + phoneString);
        return phoneString;
    }

}
