package com.inmobi.media;

import android.util.Base64;
import java.util.BitSet;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes3.dex */
public final class E1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public BitSet f3360a;

    public final void a(String b64String) {
        byte[] value;
        BitSet bitSetValueOf;
        Intrinsics.checkNotNullParameter(b64String, "b64String");
        try {
            value = Base64.decode(b64String, 0);
        } catch (Exception e) {
            Q4 q4 = Q4.f3463a;
            Q4.c.a(AbstractC1593x4.a(e, "event"));
            value = null;
        }
        if (value != null) {
            Intrinsics.checkNotNullParameter(value, "value");
            if (C1291b3.f3553a.x()) {
                bitSetValueOf = BitSet.valueOf(value);
            } else {
                Intrinsics.checkNotNullParameter(value, "<this>");
                BitSet bitSet = new BitSet(value.length * 8);
                int i = 0;
                for (byte b : value) {
                    int i2 = 0;
                    while (i2 < 8) {
                        boolean z = true;
                        int i3 = i + 1;
                        if (((b >> ((byte) i2)) & 1) != 1) {
                            z = false;
                        }
                        bitSet.set(i, z);
                        i2++;
                        i = i3;
                    }
                }
                bitSetValueOf = bitSet;
            }
            this.f3360a = bitSetValueOf;
        }
    }
}
