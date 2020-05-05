
var UpshotProfileAttribute = {

    firstName: 'firstName',
    lastName: 'lastName',
    middleName: 'middleName',
    language: 'language',
    occupation: 'occupation',
    qualification: 'qualification',
    phone: 'phone',
    localeCode: 'localeCode',
    userName: 'userName',
    email: 'email',
    age: 'age',
    gender: 'gender',
    email_opt: 'email_opt',
    sms_opt: 'sms_opt',
    push_opt: 'push_opt',
    data_opt: 'data_opt',
    ip_opt: 'ip_opt',
    appuID: 'appuID',
    facebookID: 'facebookID',
    twitterID: 'twitterID',
    foursquareID: 'foursquareID',
    linkedinID: 'linkedinID',
    googleplusID: 'googleplusID',
    enterpriseUID: 'enterpriseUID', 
    advertisingID:'advertisingID',
    instagramID: 'instagramID',
    pinterest: 'pinterest',
    year: 'year',
    month: 'month',
    day: 'day',
    lat: 'lat',
    lng: 'lng'
};


const UpshotActivityType = {
    any: -1,
    survey: 0,
    rating: 2,
    opinionPoll: 5,
    tutorials: 7,
    inAppMessage: 8,
    badges: 9,
    screenTips: 10,
    trivia: 11,
    customActions: 12,
    miniGame: 13
}


export default UpshotActivityType

module.exports = UpshotProfileAttribute
