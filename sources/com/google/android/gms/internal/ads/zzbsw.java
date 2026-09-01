package com.google.android.gms.internal.ads;

/* JADX INFO: compiled from: com.google.android.gms:play-services-ads-lite@@23.4.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbsw extends zzbhf {
    final /* synthetic */ zzbsz zza;

    /* synthetic */ zzbsw(zzbsz zzbszVar, zzbsv zzbsvVar) {
        this.zza = zzbszVar;
    }

    @Override // com.google.android.gms.internal.ads.zzbhg
    public final void zze(zzbgw zzbgwVar, String str) {
        zzbsz zzbszVar = this.zza;
        if (zzbszVar.zzb == null) {
            return;
        }
        zzbszVar.zzb.onCustomClick(zzbszVar.zzf(zzbgwVar), str);
    }
}
