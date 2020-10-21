const UpshotActivityType = {
    any: -1,
    survey: 0,
    rating: 1,
    opinionPoll: 5,
    tutorials: 7,
    inAppMessage: 8,
    badges: 9,
    screenTips: 10,
    trivia: 11,
    customActions: 12,
    miniGame: 13
}

const UpshotGender = {

    male: 1,
    female: 2,
    other: 3,
    reset: 4
}

const UpshotMaritalStatus = {

    single: 1,
    engaged: 2,
    married: 3,
    widow: 4,
    divorced: 5,
    Reset: 6,
}

const UpshotRewardHistory = {
    entire: 0,
    earn: 1,
    expiry: 2,
    redeem: 3,
    negative: 4
}

const UpshotInitOptions = {
    AppId: "bkApplicationID",
    OwnerId: "bkApplicationOwnerID",
    ExternalStorage: "bkStorageAppMemory",
    EnableLocation: "bkFetchLocation",
    EnableCrashLog: "bkExceptionHandler"    
}


export { UpshotActivityType, UpshotGender, UpshotMaritalStatus, UpshotRewardHistory, UpshotInitOptions}