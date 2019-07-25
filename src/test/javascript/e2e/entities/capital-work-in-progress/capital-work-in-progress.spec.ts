/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CapitalWorkInProgressComponentsPage,
  CapitalWorkInProgressDeleteDialog,
  CapitalWorkInProgressUpdatePage
} from './capital-work-in-progress.page-object';

const expect = chai.expect;

describe('CapitalWorkInProgress e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let capitalWorkInProgressUpdatePage: CapitalWorkInProgressUpdatePage;
  let capitalWorkInProgressComponentsPage: CapitalWorkInProgressComponentsPage;
  let capitalWorkInProgressDeleteDialog: CapitalWorkInProgressDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CapitalWorkInProgresses', async () => {
    await navBarPage.goToEntity('capital-work-in-progress');
    capitalWorkInProgressComponentsPage = new CapitalWorkInProgressComponentsPage();
    await browser.wait(ec.visibilityOf(capitalWorkInProgressComponentsPage.title), 5000);
    expect(await capitalWorkInProgressComponentsPage.getTitle()).to.eq('Capital Work In Progresses');
  });

  it('should load create CapitalWorkInProgress page', async () => {
    await capitalWorkInProgressComponentsPage.clickOnCreateButton();
    capitalWorkInProgressUpdatePage = new CapitalWorkInProgressUpdatePage();
    expect(await capitalWorkInProgressUpdatePage.getPageTitle()).to.eq('Create or edit a Capital Work In Progress');
    await capitalWorkInProgressUpdatePage.cancel();
  });

  it('should create and save CapitalWorkInProgresses', async () => {
    const nbButtonsBeforeCreate = await capitalWorkInProgressComponentsPage.countDeleteButtons();

    await capitalWorkInProgressComponentsPage.clickOnCreateButton();
    await promise.all([
      capitalWorkInProgressUpdatePage.setTransactionMonthInput('2000-12-31'),
      capitalWorkInProgressUpdatePage.setAssetSerialTagInput('assetSerialTag'),
      capitalWorkInProgressUpdatePage.setServiceOutletCodeInput('serviceOutletCode'),
      capitalWorkInProgressUpdatePage.setTransactionIdInput('5'),
      capitalWorkInProgressUpdatePage.setTransactionDetailsInput('transactionDetails'),
      capitalWorkInProgressUpdatePage.setTransactionAmountInput('5')
    ]);
    expect(await capitalWorkInProgressUpdatePage.getTransactionMonthInput()).to.eq(
      '2000-12-31',
      'Expected transactionMonth value to be equals to 2000-12-31'
    );
    expect(await capitalWorkInProgressUpdatePage.getAssetSerialTagInput()).to.eq(
      'assetSerialTag',
      'Expected AssetSerialTag value to be equals to assetSerialTag'
    );
    expect(await capitalWorkInProgressUpdatePage.getServiceOutletCodeInput()).to.eq(
      'serviceOutletCode',
      'Expected ServiceOutletCode value to be equals to serviceOutletCode'
    );
    expect(await capitalWorkInProgressUpdatePage.getTransactionIdInput()).to.eq('5', 'Expected transactionId value to be equals to 5');
    expect(await capitalWorkInProgressUpdatePage.getTransactionDetailsInput()).to.eq(
      'transactionDetails',
      'Expected TransactionDetails value to be equals to transactionDetails'
    );
    expect(await capitalWorkInProgressUpdatePage.getTransactionAmountInput()).to.eq(
      '5',
      'Expected transactionAmount value to be equals to 5'
    );
    await capitalWorkInProgressUpdatePage.save();
    expect(await capitalWorkInProgressUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await capitalWorkInProgressComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CapitalWorkInProgress', async () => {
    const nbButtonsBeforeDelete = await capitalWorkInProgressComponentsPage.countDeleteButtons();
    await capitalWorkInProgressComponentsPage.clickOnLastDeleteButton();

    capitalWorkInProgressDeleteDialog = new CapitalWorkInProgressDeleteDialog();
    expect(await capitalWorkInProgressDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Capital Work In Progress?'
    );
    await capitalWorkInProgressDeleteDialog.clickOnConfirmButton();

    expect(await capitalWorkInProgressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
