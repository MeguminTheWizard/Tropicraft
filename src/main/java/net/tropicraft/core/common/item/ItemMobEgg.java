package net.tropicraft.core.common.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tropicraft.Info;
import net.tropicraft.Names;

public class ItemMobEgg extends ItemTropicraft {

	public static String[] names = {"Iguana Egg", "Starfish Egg", "Green Frog Spawn", "Red Frog Spawn", "Yellow Frog Spawn",
		"Blue Frog Spawn", "Eye of Head", "Marlin Spawn", "Tropical Fish Spawn", "Ashen Ash", "Turtle Egg", "Man o' War", "Monkey's Paw",
		"Koa Headband", "TropiCreeper Egg", "TropiSkelly Skirt", "Spotted Eagle Ray", "Failgull", "Sea Urchin"};

	public ItemMobEgg() {
		super();
		setMaxStackSize(64);
		setHasSubtypes(true);
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp(par1ItemStack.getItemDamage(), 0, names.length - 1);
		return super.getUnlocalizedName() + "_" + Names.EGG_NAMES[i];
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
		for (int var4 = 0; var4 < names.length; ++var4) {
			list.add(new ItemStack(item, 1, var4));
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			String s = "";
			switch (itemstack.getItemDamage()) {
			case 0:
				s = "Iguana";
				break;
			case 1:
				s = "Starfish";
				break;
			case 2:
				s = "TreeFrogGreen";
				break;
			case 3:
				s = "TreeFrogRed";
				break;
			case 4:
				s = "TreeFrogYellow";
				break;
			case 5:
				s = "TreeFrogBlue";
				break;
			case 6:
				s = "EasterIslandHead";
				break;
			case 7:
				s = "Marlin";
				break;
			case 8:
				s = "TropicalFish";
				break;
			case 9:
				s = "AshenHunter";
				break;
			case 10:
				s = "SeaTurtleEgg";
				break;
			case 11:
				s = "MOW";
				break;
			case 12:
				s = "VMonkey";
				break;
			case 13:
				s = "Koa Man";
				break;
			case 14:
				s = "TropiCreeper";
				break;
			case 15:
				s = "TropiSkeleton";
				break;
			case 16:
				s = "EagleRay";
				break;
			case 17:
				s = "Failgull";
				break;
			case 18:
				s = "SeaUrchin";
				break;
			default:
				return EnumActionResult.PASS;
			}

			if (s.equals("Koa Man")) {
				Random rand = new Random();
				int choice = rand.nextInt(2);
				s = "KoaHunter";
				if (choice == 1) s = "KoaFisher";
			}

			StringBuilder sb = new StringBuilder();
			sb.append(Info.MODID);
			sb.append(".");
			sb.append(s);
			s = sb.toString();

			pos = pos.offset(facing);
			double d0 = 0.0D;

			IBlockState iblockstate = world.getBlockState(pos);

			if (facing == EnumFacing.UP && iblockstate.getBlock() instanceof BlockFence) //Forge: Fix Vanilla bug comparing state instead of block {
				d0 = 0.5D;

			spawnCreature(world, s, (double)pos.getX() + 0.5D, (double)pos.getY() + d0, (double)pos.getZ() + 0.5D);
			player.swingArm(EnumHand.MAIN_HAND);
		}
		return EnumActionResult.PASS;
	}

	/**
	 * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
	 * Parameters: world, entityID, x, y, z.
	 */
	@Nullable
	public static Entity spawnCreature(World worldIn, @Nullable String entityID, double x, double y, double z) {
		if (entityID != null) {
			Entity entity = null;

			for (int i = 0; i < 1; ++i) {
				entity = EntityList.createEntityByIDFromName(entityID, worldIn);

				if (entity instanceof EntityLivingBase) {
					EntityLiving entityliving = (EntityLiving)entity;
					entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
					entityliving.rotationYawHead = entityliving.rotationYaw;
					entityliving.renderYawOffset = entityliving.rotationYaw;
					entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData)null);
					worldIn.spawnEntity(entity);
					entityliving.playLivingSound();
				}
			}

			return entity;
		}
		else
		{
			return null;
		}
	}
}