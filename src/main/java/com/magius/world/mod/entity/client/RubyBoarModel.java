package com.magius.world.mod.entity.client;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.custom.RubyBoarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class RubyBoarModel extends EntityModel<RubyBoarEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "rubyboarmodel"), "main");
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leg_front_left;
	private final ModelPart leg_front_right;
	private final ModelPart leg_back_left;
	private final ModelPart leg_back_right;

	public RubyBoarModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.leg_front_left = root.getChild("leg_front_left");
		this.leg_front_right = root.getChild("leg_front_right");
		this.leg_back_left = root.getChild("leg_back_left");
		this.leg_back_right = root.getChild("leg_back_right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(4.0F, 16.0F, 0.0F));

		PartDefinition back_pick_right_r1 = body.addOrReplaceChild("back_pick_right_r1", CubeListBuilder.create().texOffs(23, 29).addBox(1.0F, 10.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 8.0F, 0.0F, -0.2182F, 0.3491F, 3.0543F));

		PartDefinition back_pick_left_r1 = body.addOrReplaceChild("back_pick_left_r1", CubeListBuilder.create().texOffs(23, 29).addBox(-2.0F, 10.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 8.0F, 0.0F, 0.0F, -0.1309F, -3.0543F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(1, 1).addBox(-4.0F, 4.0F, -6.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 8.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 38).addBox(7.0F, 0.0F, -12.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 38).addBox(0.0F, 0.0F, -13.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 38).addBox(0.0F, 1.0F, -15.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 38).addBox(0.0F, 2.0F, -17.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 38).addBox(7.0F, 1.0F, -14.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 38).addBox(7.0F, 2.0F, -16.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 17.0F, 0.0F));

		PartDefinition snout_r1 = head.addOrReplaceChild("snout_r1", CubeListBuilder.create().texOffs(45, 5).addBox(-1.0F, 5.0F, -13.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(37, 14).addBox(-3.0F, 4.0F, -11.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 7.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition leg_front_left = partdefinition.addOrReplaceChild("leg_front_left", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, -4.0F));

		PartDefinition leg_front_left_r1 = leg_front_left.addOrReplaceChild("leg_front_left_r1", CubeListBuilder.create().texOffs(5, 27).addBox(-4.0F, 0.0F, -4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 4.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition leg_front_right = partdefinition.addOrReplaceChild("leg_front_right", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, -3.0F));

		PartDefinition leg_front_right_r1 = leg_front_right.addOrReplaceChild("leg_front_right_r1", CubeListBuilder.create().texOffs(5, 27).addBox(2.0F, 0.0F, -4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 3.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition leg_back_left = partdefinition.addOrReplaceChild("leg_back_left", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 4.0F));

		PartDefinition leg_back_left_r1 = leg_back_left.addOrReplaceChild("leg_back_left_r1", CubeListBuilder.create().texOffs(5, 27).addBox(-4.0F, 0.0F, 4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -4.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition leg_back_right = partdefinition.addOrReplaceChild("leg_back_right", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 4.0F));

		PartDefinition leg_back_right_r1 = leg_back_right.addOrReplaceChild("leg_back_right_r1", CubeListBuilder.create().texOffs(5, 27).addBox(2.0F, 0.0F, 4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -4.0F, 0.0F, 0.0F, -3.1416F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(RubyBoarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// Reset basique
		this.head.xRot = 0.0F;
		this.head.yRot = 0.0F;

		this.leg_front_left.xRot = 0.0F;
		this.leg_front_right.xRot = 0.0F;
		this.leg_back_left.xRot = 0.0F;
		this.leg_back_right.xRot = 0.0F;

		// Tête : regarde légèrement le joueur
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F) * 0.6F;
		this.head.xRot = headPitch * ((float)Math.PI / 180F) * 0.4F;

		// Marche : avant gauche + arrière droite ensemble
		this.leg_front_left.xRot = Mth.cos(limbSwing * 0.6662F) * 1.1F * limbSwingAmount;
		this.leg_back_right.xRot = Mth.cos(limbSwing * 0.6662F) * 1.1F * limbSwingAmount;

		// Marche : avant droite + arrière gauche en opposition
		this.leg_front_right.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.1F * limbSwingAmount;
		this.leg_back_left.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.1F * limbSwingAmount;

		// Idle léger : petite respiration / mouvement de tête
		this.head.xRot += Mth.sin(ageInTicks * 0.08F) * 0.03F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg_front_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg_front_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg_back_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg_back_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}