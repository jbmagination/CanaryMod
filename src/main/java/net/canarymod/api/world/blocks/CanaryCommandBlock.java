package net.canarymod.api.world.blocks;

import net.canarymod.Canary;
import net.canarymod.api.Server;
import net.canarymod.api.chat.ChatComponent;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.chat.ReceiverType;
import net.canarymod.config.Configuration;
import net.canarymod.exceptions.InvalidInstanceException;
import net.canarymod.hook.system.PermissionCheckHook;
import net.canarymod.logger.Logman;
import net.canarymod.user.Group;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;

import static net.canarymod.Canary.log;

/**
 * CommandBlock wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryCommandBlock extends CanaryTileEntity implements CommandBlock {
    private static final String cmdPrefix = "[CommandBlock:%s] %s";
    Group group; // The group for permission checking

    /**
     * Constructs a wrapper for TileEntityCommandBlock
     *
     * @param tileentity
     *         the TileEntityCommandBlock to wrap
     */
    public CanaryCommandBlock(TileEntityCommandBlock tileentity) {
        super(tileentity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getLogic().d_();
    }

    @Override
    public void notice(String message) {
        log.info(Logman.NOTICE, String.format(cmdPrefix, getName(), message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(String name) {
        getLogic().b(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notice(CharSequence message) {
        if (!Configuration.getServerConfig().isCommandBlockSilent()) {
            log.info(Logman.NOTICE, String.format(cmdPrefix, getName(), message));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notice(CharSequence... messages) {
        for (CharSequence message : messages) {
            notice(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notice(Iterable<? extends CharSequence> messages) {
        for (CharSequence message : messages) {
            notice(message);
        }
    }

    @Override
    public void message(String message) {
        if (!Configuration.getServerConfig().isCommandBlockSilent()) {
            log.info(Logman.MESSAGE, String.format(cmdPrefix, getName(), message));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void message(CharSequence message) {
        this.message(message.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void message(CharSequence... messages) {
        for (CharSequence message : messages) {
            this.message(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void message(Iterable<? extends CharSequence> messages) {
        for (CharSequence message : messages) {
            this.message(message);
        }
    }

    @Override
    public void message(ChatComponent... chatComponents) {
        for(ChatComponent chatComponent : chatComponents){
            this.message(chatComponent.getFullText());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(String node) {
        return Configuration.getServerConfig().isCommandBlockOpped() || (getGroup() != null && ((PermissionCheckHook) new PermissionCheckHook(node, this, group.hasPermission(node)).call()).getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean safeHasPermission(String node) {
        return Configuration.getServerConfig().isCommandBlockOpped() || (getGroup() != null && group.hasPermission(node));
    }

    @Override
    public ReceiverType getReceiverType() {
        return ReceiverType.COMMANDBLOCK;
    }

    @Override
    public Player asPlayer() {
        throw new InvalidInstanceException("CommandBlock is not a MessageReceiver of the type: PLAYER");
    }

    @Override
    public Server asServer() {
        throw new InvalidInstanceException("CommandBlock is not a MessageReceiver of the type: SERVER");
    }

    @Override
    public net.canarymod.api.CommandBlockLogic asCommandBlock() {
        return this;
    }

    @Override
    public String getLocale() {
        return Configuration.getServerConfig().getServerLocale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommand(String command) {
        getLogic().a(command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommand() {
        return getLogic().l();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        getLogic().a(((CanaryWorld) getWorld()).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileEntityCommandBlock getTileEntity() {
        return (TileEntityCommandBlock) tileentity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group getGroup() {
        if (group == null) {
            String gName = Configuration.getServerConfig().getCommandBlockGroupName();
            if (gName != null && Canary.usersAndGroups().groupExists(gName)) {
                group = Canary.usersAndGroups().getGroup(gName);
            } else {
                Canary.log.warn("CommandBlock has a bad group configuration... Please check your config and fix this.");
                group = Canary.usersAndGroups().getDefaultGroup();
            }
        }
        return group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    public CommandBlockLogic getLogic() {
        return getTileEntity().getLogic();
    }

}
