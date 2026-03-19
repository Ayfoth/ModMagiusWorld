package com.magius.world.mod.entity.client;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.magius.world.mod.MagiusWorldMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RubyWispModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "rubywispmodel"), "main");
	private final ModelPart core;
	private final ModelPart shell;
	private final ModelPart shard_top;
	private final ModelPart shard_left;
	private final ModelPart shard_right;

	public RubyWispModel(ModelPart root) {
		this.core = root.getChild("core");
		this.shell = root.getChild("shell");
		this.shard_top = root.getChild("shard_top");
		this.shard_left = root.getChild("shard_left");
		this.shard_right = root.getChild("shard_right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create().texOffs(4, 20).addBox(-3.0F, -13.0F, -5.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition shell = partdefinition.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -15.0F, -5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition shard_top = partdefinition.addOrReplaceChild("shard_top", CubeListBuilder.create().texOffs(13, 10).addBox(-2.0F, -22.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition shard_left = partdefinition.addOrReplaceChild("shard_left", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition shard_left_r1 = shard_left.addOrReplaceChild("shard_left_r1", CubeListBuilder.create().texOffs(13, 10).addBox(-1.0F, -15.0F, -3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition shard_right = partdefinition.addOrReplaceChild("shard_right", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition shard_right_r1 = shard_right.addOrReplaceChild("shard_right_r1", CubeListBuilder.create().texOffs(13, 10).addBox(-3.0F, -15.0F, -3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		// flottement
		float floatOffset = (float)Math.sin(ageInTicks * 0.2F) * 1.5F;

		this.core.y = floatOffset;
		this.shell.y = floatOffset;
		this.shard_top.y = floatOffset;
		this.shard_left.y = floatOffset;
		this.shard_right.y = floatOffset;

		// rotation magique
		this.shard_left.zRot = ageInTicks * 0.1F;
		this.shard_right.zRot = -ageInTicks * 0.1F;
		this.shard_top.xRot = ageInTicks * 0.08F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		core.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		shell.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		shard_top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		shard_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		shard_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}