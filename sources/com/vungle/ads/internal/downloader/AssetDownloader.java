package com.vungle.ads.internal.downloader;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.json.v8;
import com.vungle.ads.AnalyticsClient;
import com.vungle.ads.OutOfMemory;
import com.vungle.ads.internal.ConfigManager;
import com.vungle.ads.internal.downloader.AssetDownloadListener;
import com.vungle.ads.internal.executor.VungleThreadPoolExecutor;
import com.vungle.ads.internal.presenter.NativeAdPresenter;
import com.vungle.ads.internal.task.PriorityRunnable;
import com.vungle.ads.internal.ui.AdActivity;
import com.vungle.ads.internal.util.Logger;
import com.vungle.ads.internal.util.PathProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import okio.Okio;

/* JADX INFO: compiled from: AssetDownloader.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 %2\u00020\u0001:\u0001%B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010\u000f\u001a\u00020\rH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J$\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u000b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002J\"\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\u000b2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0019H\u0002J\u001c\u0010 \u001a\u00020\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0012\u0010!\u001a\u00020\u00112\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\u001a\u0010$\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u000b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/vungle/ads/internal/downloader/AssetDownloader;", "Lcom/vungle/ads/internal/downloader/Downloader;", "downloadExecutor", "Lcom/vungle/ads/internal/executor/VungleThreadPoolExecutor;", "pathProvider", "Lcom/vungle/ads/internal/util/PathProvider;", "(Lcom/vungle/ads/internal/executor/VungleThreadPoolExecutor;Lcom/vungle/ads/internal/util/PathProvider;)V", "okHttpClient", "Lokhttp3/OkHttpClient;", "transitioning", "", "Lcom/vungle/ads/internal/downloader/DownloadRequest;", "cancel", "", AdActivity.REQUEST_KEY_EXTRA, "cancelAll", "checkSpaceAvailable", "", "decodeGzipIfNeeded", "Lokhttp3/ResponseBody;", "networkResponse", "Lokhttp3/Response;", "deliverError", "downloadRequest", "downloadListener", "Lcom/vungle/ads/internal/downloader/AssetDownloadListener;", "downloadError", "Lcom/vungle/ads/internal/downloader/AssetDownloadListener$DownloadError;", "deliverSuccess", v8.h.b, "Ljava/io/File;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, NativeAdPresenter.DOWNLOAD, "isValidUrl", "httpUrl", "", "launchRequest", "Companion", "vungle-ads_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
public final class AssetDownloader implements Downloader {
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final int DOWNLOAD_CHUNK_SIZE = 2048;
    private static final String GZIP = "gzip";
    private static final String IDENTITY = "identity";
    private static final int MINIMUM_SPACE_REQUIRED_MB = 20971520;
    private static final String TAG = "AssetDownloader";
    private static final int TIMEOUT = 30;
    private final VungleThreadPoolExecutor downloadExecutor;
    private OkHttpClient okHttpClient;
    private final PathProvider pathProvider;
    private final List<DownloadRequest> transitioning;

    public AssetDownloader(VungleThreadPoolExecutor downloadExecutor, PathProvider pathProvider) {
        Intrinsics.checkNotNullParameter(downloadExecutor, "downloadExecutor");
        Intrinsics.checkNotNullParameter(pathProvider, "pathProvider");
        this.downloadExecutor = downloadExecutor;
        this.pathProvider = pathProvider;
        this.transitioning = new ArrayList();
        OkHttpClient.Builder builderFollowSslRedirects = new OkHttpClient.Builder().readTimeout(30L, TimeUnit.SECONDS).connectTimeout(30L, TimeUnit.SECONDS).cache(null).followRedirects(true).followSslRedirects(true);
        if (ConfigManager.INSTANCE.isCleverCacheEnabled()) {
            long cleverCacheDiskSize = ConfigManager.INSTANCE.getCleverCacheDiskSize();
            int cleverCacheDiskPercentage = ConfigManager.INSTANCE.getCleverCacheDiskPercentage();
            String absolutePath = pathProvider.getCleverCacheDir().getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "pathProvider.getCleverCacheDir().absolutePath");
            long jMin = Long.min(cleverCacheDiskSize, (pathProvider.getAvailableBytes(absolutePath) * ((long) cleverCacheDiskPercentage)) / ((long) 100));
            if (jMin > 0) {
                builderFollowSslRedirects.cache(new Cache(pathProvider.getCleverCacheDir(), jMin));
            } else {
                Logger.INSTANCE.w(TAG, "cache disk capacity size <=0, no clever cache active.");
            }
        }
        this.okHttpClient = builderFollowSslRedirects.build();
    }

    @Override // com.vungle.ads.internal.downloader.Downloader
    public void download(final DownloadRequest downloadRequest, final AssetDownloadListener downloadListener) {
        if (downloadRequest == null) {
            return;
        }
        this.transitioning.add(downloadRequest);
        this.downloadExecutor.execute(new PriorityRunnable() { // from class: com.vungle.ads.internal.downloader.AssetDownloader.download.1
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                AssetDownloader.this.launchRequest(downloadRequest, downloadListener);
            }

            @Override // com.vungle.ads.internal.task.PriorityRunnable
            public int getPriority() {
                return downloadRequest.getPriority();
            }
        }, new Runnable() { // from class: com.vungle.ads.internal.downloader.AssetDownloader$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AssetDownloader.m3436download$lambda0(this.f$0, downloadRequest, downloadListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: download$lambda-0, reason: not valid java name */
    public static final void m3436download$lambda0(AssetDownloader this$0, DownloadRequest downloadRequest, AssetDownloadListener assetDownloadListener) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.deliverError(downloadRequest, assetDownloadListener, new AssetDownloadListener.DownloadError(-1, new OutOfMemory("Cannot complete " + downloadRequest + " : Out of Memory"), AssetDownloadListener.DownloadError.ErrorReason.INSTANCE.getINTERNAL_ERROR()));
    }

    private final void deliverError(DownloadRequest downloadRequest, AssetDownloadListener downloadListener, AssetDownloadListener.DownloadError downloadError) {
        if (downloadListener != null) {
            downloadListener.onError(downloadError, downloadRequest);
        }
    }

    @Override // com.vungle.ads.internal.downloader.Downloader
    public void cancel(DownloadRequest request) {
        if (request == null || request.isCancelled()) {
            return;
        }
        request.cancel();
    }

    @Override // com.vungle.ads.internal.downloader.Downloader
    public void cancelAll() {
        Iterator<T> it = this.transitioning.iterator();
        while (it.hasNext()) {
            cancel((DownloadRequest) it.next());
        }
        this.transitioning.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /*  JADX ERROR: Type inference failed with stack overflow
        jadx.core.utils.exceptions.JadxOverflowException
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public final void launchRequest(com.vungle.ads.internal.downloader.DownloadRequest r39, com.vungle.ads.internal.downloader.AssetDownloadListener r40) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 1877
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vungle.ads.internal.downloader.AssetDownloader.launchRequest(com.vungle.ads.internal.downloader.DownloadRequest, com.vungle.ads.internal.downloader.AssetDownloadListener):void");
    }

    private final ResponseBody decodeGzipIfNeeded(Response networkResponse) {
        ResponseBody responseBodyBody = networkResponse.body();
        if (!StringsKt.equals(GZIP, Response.header$default(networkResponse, "Content-Encoding", null, 2, null), true) || responseBodyBody == null) {
            return responseBodyBody;
        }
        return new RealResponseBody(Response.header$default(networkResponse, "Content-Type", null, 2, null), -1L, Okio.buffer(new GzipSource(responseBodyBody.getDelegateSource())));
    }

    private final void deliverSuccess(File file, DownloadRequest downloadRequest, AssetDownloadListener listener) {
        Logger.INSTANCE.d(TAG, "On success " + downloadRequest);
        if (listener != null) {
            listener.onSuccess(file, downloadRequest);
        }
    }

    private final boolean checkSpaceAvailable() throws Throwable {
        PathProvider pathProvider = this.pathProvider;
        String absolutePath = pathProvider.getVungleDir().getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "pathProvider.getVungleDir().absolutePath");
        long availableBytes = pathProvider.getAvailableBytes(absolutePath);
        if (availableBytes >= 20971520) {
            return true;
        }
        AnalyticsClient.logError$vungle_ads_release$default(AnalyticsClient.INSTANCE, 126, "Insufficient space " + availableBytes, (String) null, (String) null, (String) null, 28, (Object) null);
        return false;
    }

    private final boolean isValidUrl(String httpUrl) {
        String str = httpUrl;
        return (str == null || str.length() == 0 || HttpUrl.INSTANCE.parse(httpUrl) == null) ? false : true;
    }
}
