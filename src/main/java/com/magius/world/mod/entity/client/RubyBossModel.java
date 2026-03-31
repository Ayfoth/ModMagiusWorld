package com.magius.world.mod.entity.client;


import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.custom.RubyBossEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 5.1.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class RubyBossModel<T extends RubyBossEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_boss"), "main");
	private final ModelPart tete;
	private final ModelPart torse;
	private final ModelPart bras;
	private final ModelPart bras_droite;
	private final ModelPart bras_gauche;
	private final ModelPart jambes;
	private final ModelPart jambe_droite;
	private final ModelPart jambe_gauche;

	public RubyBossModel(ModelPart root) {
		this.tete = root.getChild("tete");
		this.torse = root.getChild("torse");
		this.bras = root.getChild("bras");
		this.bras_droite = this.bras.getChild("bras_droite");
		this.bras_gauche = this.bras.getChild("bras_gauche");
		this.jambes = root.getChild("jambes");
		this.jambe_droite = this.jambes.getChild("jambe_droite");
		this.jambe_gauche = this.jambes.getChild("jambe_gauche");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tete = partdefinition.addOrReplaceChild("tete", CubeListBuilder.create().texOffs(32, 48).addBox(0.0F, -20.0F, -2.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 14.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition torse = partdefinition.addOrReplaceChild("torse", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -13.0F, -1.0F, 14.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(26, 25).addBox(2.0F, -10.0F, 3.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 14.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cristal_corrompu_r1 = torse.addOrReplaceChild("cristal_corrompu_r1", CubeListBuilder.create().texOffs(6, 51).addBox(6.0F, -4.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -5.0F, -3.0F, 0.3491F, 0.3491F, 0.0F));

		PartDefinition cristal_corrompu_r2 = torse.addOrReplaceChild("cristal_corrompu_r2", CubeListBuilder.create().texOffs(6, 51).addBox(6.0F, -4.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, -3.0F, -0.3491F, -0.3927F, 0.0F));

		PartDefinition bras = partdefinition.addOrReplaceChild("bras", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 14.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bras_droite = bras.addOrReplaceChild("bras_droite", CubeListBuilder.create().texOffs(52, 32).addBox(-12.0F, -17.0F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(44, 7).addBox(3.0F, -15.0F, -1.0F, 6.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 4.0F, 0.0F));

		PartDefinition cristal_corrompu_r3 = bras_droite.addOrReplaceChild("cristal_corrompu_r3", CubeListBuilder.create().texOffs(7, 51).addBox(7.0F, -3.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -6.0F, 3.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition bras_gauche = bras.addOrReplaceChild("bras_gauche", CubeListBuilder.create().texOffs(46, 7).addBox(4.0F, -15.0F, -1.0F, 5.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(52, 32).addBox(22.0F, -17.0F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 4.0F, 0.0F));

		PartDefinition cristal_corrompu_r4 = bras_gauche.addOrReplaceChild("cristal_corrompu_r4", CubeListBuilder.create().texOffs(8, 52).addBox(7.0F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -5.0F, 4.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition jambes = partdefinition.addOrReplaceChild("jambes", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 24.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition jambe_droite = jambes.addOrReplaceChild("jambe_droite", CubeListBuilder.create().texOffs(6, 31).addBox(3.0F, -11.0F, -1.0F, 6.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.0F, 0.0F));

		PartDefinition jambe_gauche = jambes.addOrReplaceChild("jambe_gauche", CubeListBuilder.create().texOffs(6, 31).addBox(3.0F, -11.0F, -1.0F, 6.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(RubyBossEntity entity, float limbSwing, float limbSwingAmount,
						  float ageInTicks, float netHeadYaw, float headPitch) {

		// Reset (IMPORTANT pour éviter bugs visuels)
		this.tete.xRot = 0;
		this.tete.yRot = 0;
		this.bras_droite.xRot = 0;
		this.bras_gauche.xRot = 0;
		this.jambe_droite.xRot = 0;
		this.jambe_gauche.xRot = 0;

		// 🎯 REGARD (tête suit le joueur)
		// Tête
		this.tete.yRot = (float)Math.PI - netHeadYaw * ((float)Math.PI / 180F);
		this.tete.xRot = -headPitch * ((float)Math.PI / 180F);

		// 🚶 ANIMATION DE MARCHE (type zombie amélioré)
		float speed = 0.6F; // vitesse anim
		float amplitude = 1.2F; // intensité

		this.jambe_droite.xRot = (float)Math.cos(limbSwing * speed) * amplitude * limbSwingAmount;
		this.jambe_gauche.xRot = (float)Math.cos(limbSwing * speed + Math.PI) * amplitude * limbSwingAmount;

		// bras opposés aux jambes (naturel)
		this.bras_droite.xRot = (float)Math.cos(limbSwing * speed + Math.PI) * amplitude * limbSwingAmount;
		this.bras_gauche.xRot = (float)Math.cos(limbSwing * speed) * amplitude * limbSwingAmount;

		// 💀 effet boss : léger mouvement idle (respiration)
		this.torse.y = 14.0F + (float)Math.sin(ageInTicks * 0.1F) * 0.5F;

		// 🔥 effet "puissance" : petits tremblements bras
		this.bras_droite.zRot = (float)Math.sin(ageInTicks * 0.2F) * 0.05F;
		this.bras_gauche.zRot = (float)Math.cos(ageInTicks * 0.2F) * 0.05F;

		if (entity.isAggressive()) {
			this.bras_droite.xRot = -1.5F;
			this.bras_gauche.xRot = -1.5F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tete.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		torse.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bras.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		jambes.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}