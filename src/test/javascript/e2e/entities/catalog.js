'use strict';

describe('Catalog e2e test', function () {

    var username = element(by.id('username'));
    var password = element(by.id('password'));
    var entityMenu = element(by.id('entity-menu'));
    var accountMenu = element(by.id('account-menu'));
    var catalog = element(by.id('catalog'));
    var login = element(by.id('login'));
    var logout = element(by.id('logout'));

    beforeAll(function () {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
    });

    it('should load Catalog', function () {
        catalog.click();
        element.all(by.css('[ui-sref="catalog"]')).first().click().then(function() {
            expect(element.all(by.css('h2')).first().getText()).toMatch(/Catalog/);
        });
    });

    afterAll(function () {
        accountMenu.click();
        logout.click();
    });
});
