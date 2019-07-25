/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DealerComponentsPage, DealerDeleteDialog, DealerUpdatePage } from './dealer.page-object';

const expect = chai.expect;

describe('Dealer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dealerUpdatePage: DealerUpdatePage;
  let dealerComponentsPage: DealerComponentsPage;
  let dealerDeleteDialog: DealerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Dealers', async () => {
    await navBarPage.goToEntity('dealer');
    dealerComponentsPage = new DealerComponentsPage();
    await browser.wait(ec.visibilityOf(dealerComponentsPage.title), 5000);
    expect(await dealerComponentsPage.getTitle()).to.eq('Dealers');
  });

  it('should load create Dealer page', async () => {
    await dealerComponentsPage.clickOnCreateButton();
    dealerUpdatePage = new DealerUpdatePage();
    expect(await dealerUpdatePage.getPageTitle()).to.eq('Create or edit a Dealer');
    await dealerUpdatePage.cancel();
  });

  it('should create and save Dealers', async () => {
    const nbButtonsBeforeCreate = await dealerComponentsPage.countDeleteButtons();

    await dealerComponentsPage.clickOnCreateButton();
    await promise.all([
      dealerUpdatePage.titleSelectLastOption(),
      dealerUpdatePage.setDealerNameInput('dealerName'),
      dealerUpdatePage.setDealerAddressInput('dealerAddress'),
      dealerUpdatePage.setDealerPhoneNumberInput('dealerPhoneNumber'),
      dealerUpdatePage.setDealerEmailInput('dealerEmail'),
      dealerUpdatePage.setBankNameInput('bankName'),
      dealerUpdatePage.setBankAccountNumberInput('bankAccountNumber'),
      dealerUpdatePage.setBankBranchInput('bankBranch'),
      dealerUpdatePage.setBankSwiftCodeInput('bankSwiftCode'),
      dealerUpdatePage.setBankPhysicalAddressInput('bankPhysicalAddress'),
      dealerUpdatePage.setDomicileInput('domicile'),
      dealerUpdatePage.setTaxAuthorityRefInput('taxAuthorityRef')
    ]);
    expect(await dealerUpdatePage.getDealerNameInput()).to.eq('dealerName', 'Expected DealerName value to be equals to dealerName');
    expect(await dealerUpdatePage.getDealerAddressInput()).to.eq(
      'dealerAddress',
      'Expected DealerAddress value to be equals to dealerAddress'
    );
    expect(await dealerUpdatePage.getDealerPhoneNumberInput()).to.eq(
      'dealerPhoneNumber',
      'Expected DealerPhoneNumber value to be equals to dealerPhoneNumber'
    );
    expect(await dealerUpdatePage.getDealerEmailInput()).to.eq('dealerEmail', 'Expected DealerEmail value to be equals to dealerEmail');
    expect(await dealerUpdatePage.getBankNameInput()).to.eq('bankName', 'Expected BankName value to be equals to bankName');
    expect(await dealerUpdatePage.getBankAccountNumberInput()).to.eq(
      'bankAccountNumber',
      'Expected BankAccountNumber value to be equals to bankAccountNumber'
    );
    expect(await dealerUpdatePage.getBankBranchInput()).to.eq('bankBranch', 'Expected BankBranch value to be equals to bankBranch');
    expect(await dealerUpdatePage.getBankSwiftCodeInput()).to.eq(
      'bankSwiftCode',
      'Expected BankSwiftCode value to be equals to bankSwiftCode'
    );
    expect(await dealerUpdatePage.getBankPhysicalAddressInput()).to.eq(
      'bankPhysicalAddress',
      'Expected BankPhysicalAddress value to be equals to bankPhysicalAddress'
    );
    expect(await dealerUpdatePage.getDomicileInput()).to.eq('domicile', 'Expected Domicile value to be equals to domicile');
    expect(await dealerUpdatePage.getTaxAuthorityRefInput()).to.eq(
      'taxAuthorityRef',
      'Expected TaxAuthorityRef value to be equals to taxAuthorityRef'
    );
    await dealerUpdatePage.save();
    expect(await dealerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dealerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Dealer', async () => {
    const nbButtonsBeforeDelete = await dealerComponentsPage.countDeleteButtons();
    await dealerComponentsPage.clickOnLastDeleteButton();

    dealerDeleteDialog = new DealerDeleteDialog();
    expect(await dealerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Dealer?');
    await dealerDeleteDialog.clickOnConfirmButton();

    expect(await dealerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
