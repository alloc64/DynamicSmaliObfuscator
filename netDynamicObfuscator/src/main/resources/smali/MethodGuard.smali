.class public Lnet/dynamico/MethodGuard;
.super Ljava/lang/Object;
.source "MethodGuard.java"


# static fields
.field private static isValidState:Z

.field private static lastPropertyCheckTimestamp:J


# direct methods
.method static constructor <clinit>()V
    .registers 2

    .prologue
    .line 11
    const-wide/16 v0, -0x1

    sput-wide v0, Lnet/dynamico/MethodGuard;->lastPropertyCheckTimestamp:J

    .line 12
    const/4 v0, 0x0

    sput-boolean v0, Lnet/dynamico/MethodGuard;->isValidState:Z

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static fail()V
    .registers 5

    .prologue
    .line 27
    const/4 v0, 0x0

    .line 29
    .local v0, "i":I
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    .line 33
    .local v3, "sb":Ljava/lang/StringBuilder;
    :goto_6
    const/16 v4, 0xc1

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 34
    const/4 v4, 0x3

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 36
    const/4 v2, 0x0

    .local v2, "j":I
    :goto_10
    const/16 v4, 0x8

    if-ge v2, v4, :cond_54

    .line 38
    const/16 v4, 0xd

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 39
    const/16 v4, 0x2d

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 40
    const/16 v4, 0x11

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 41
    const/16 v4, 0x5d

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 42
    const/16 v4, 0xb6

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 43
    const/4 v4, 0x0

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 44
    const/16 v4, 0x3c

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 45
    const/16 v4, 0x16

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 46
    const/16 v4, 0x63

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 47
    const/16 v4, 0x31

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 48
    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 49
    const/16 v4, 0x30

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 36
    add-int/lit8 v2, v2, 0x1

    goto :goto_10

    .line 52
    :cond_54
    add-int/lit8 v1, v0, 0x1

    .end local v0    # "i":I
    .local v1, "i":I
    const v4, 0x198cb

    if-lt v0, v4, :cond_5c

    .line 53
    return-void

    :cond_5c
    move v0, v1

    .end local v1    # "i":I
    .restart local v0    # "i":I
    goto :goto_6
.end method

.method public static initialized()Z
    .registers 4

    .prologue
    .line 16
    sget-wide v0, Lnet/dynamico/MethodGuard;->lastPropertyCheckTimestamp:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-ltz v0, :cond_15

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    sget-wide v2, Lnet/dynamico/MethodGuard;->lastPropertyCheckTimestamp:J

    sub-long/2addr v0, v2

    const-wide/16 v2, 0x2710

    cmp-long v0, v0, v2

    if-lez v0, :cond_27

    .line 18
    :cond_15
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    sput-wide v0, Lnet/dynamico/MethodGuard;->lastPropertyCheckTimestamp:J

    .line 19
    const-string v0, "__0"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Ljava/lang/System;->getProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_2a

    const/4 v0, 0x1

    :goto_25
    sput-boolean v0, Lnet/dynamico/MethodGuard;->isValidState:Z

    .line 22
    :cond_27
    sget-boolean v0, Lnet/dynamico/MethodGuard;->isValidState:Z

    return v0

    .line 19
    :cond_2a
    const/4 v0, 0x0

    goto :goto_25
.end method
