package br.com.fgr.emergencia.models.events;

public class PermissionEvent {

    private String permission;

    public PermissionEvent(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
