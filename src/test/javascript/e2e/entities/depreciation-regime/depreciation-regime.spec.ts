/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DepreciationRegimeComponentsPage,
  DepreciationRegimeDeleteDialog,
  DepreciationRegimeUpdatePage
} from './depreciation-regime.page-object';

const expect = chai.expect;

describe('DepreciationRegime e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let depreciationRegimeUpdatePage: DepreciationRegimeUpdatePage;
  let depreciationRegimeComponentsPage: DepreciationRegimeComponentsPage;
  let depreciationRegimeDeleteDialog: DepreciationRegimeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DepreciationRegimes', async () => {
    await navBarPage.goToEntity('depreciation-regime');
    depreciationRegimeComponentsPage = new DepreciationRegimeComponentsPage();
    await browser.wait(ec.visibilityOf(depreciationRegimeComponentsPage.title), 5000);
    expect(await depreciationRegimeComponentsPage.getTitle()).to.eq('Depreciation Regimes');
  });

  it('should load create DepreciationRegime page', async () => {
    await depreciationRegimeComponentsPage.clickOnCreateButton();
    depreciationRegimeUpdatePage = new DepreciationRegimeUpdatePage();
    expect(await depreciationRegimeUpdatePage.getPageTitle()).to.eq('Create or edit a Depreciation Regime');
    await depreciationRegimeUpdatePage.cancel();
  });

  it('should create and save DepreciationRegimes', async () => {
    const nbButtonsBeforeCreate = await depreciationRegimeComponentsPage.countDeleteButtons();

    await depreciationRegimeComponentsPage.clickOnCreateButton();
    await promise.all([
      depreciationRegimeUpdatePage.assetDecayTypeSelectLastOption(),
      depreciationRegimeUpdatePage.setDepreciationRateInput('5'),
      depreciationRegimeUpdatePage.setDescriptionInput('description')
    ]);
    expect(await depreciationRegimeUpdatePage.getDepreciationRateInput()).to.eq('5', 'Expected depreciationRate value to be equals to 5');
    expect(await depreciationRegimeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    await depreciationRegimeUpdatePage.save();
    expect(await depreciationRegimeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await depreciationRegimeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DepreciationRegime', async () => {
    const nbButtonsBeforeDelete = await depreciationRegimeComponentsPage.countDeleteButtons();
    await depreciationRegimeComponentsPage.clickOnLastDeleteButton();

    depreciationRegimeDeleteDialog = new DepreciationRegimeDeleteDialog();
    expect(await depreciationRegimeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Depreciation Regime?');
    await depreciationRegimeDeleteDialog.clickOnConfirmButton();

    expect(await depreciationRegimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
