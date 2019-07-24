/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AssetDepreciationComponentsPage,
  AssetDepreciationDeleteDialog,
  AssetDepreciationUpdatePage
} from './asset-depreciation.page-object';

const expect = chai.expect;

describe('AssetDepreciation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let assetDepreciationUpdatePage: AssetDepreciationUpdatePage;
  let assetDepreciationComponentsPage: AssetDepreciationComponentsPage;
  let assetDepreciationDeleteDialog: AssetDepreciationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AssetDepreciations', async () => {
    await navBarPage.goToEntity('asset-depreciation');
    assetDepreciationComponentsPage = new AssetDepreciationComponentsPage();
    await browser.wait(ec.visibilityOf(assetDepreciationComponentsPage.title), 5000);
    expect(await assetDepreciationComponentsPage.getTitle()).to.eq('Asset Depreciations');
  });

  it('should load create AssetDepreciation page', async () => {
    await assetDepreciationComponentsPage.clickOnCreateButton();
    assetDepreciationUpdatePage = new AssetDepreciationUpdatePage();
    expect(await assetDepreciationUpdatePage.getPageTitle()).to.eq('Create or edit a Asset Depreciation');
    await assetDepreciationUpdatePage.cancel();
  });

  it('should create and save AssetDepreciations', async () => {
    const nbButtonsBeforeCreate = await assetDepreciationComponentsPage.countDeleteButtons();

    await assetDepreciationComponentsPage.clickOnCreateButton();
    await promise.all([
      assetDepreciationUpdatePage.setDescriptionInput('description'),
      assetDepreciationUpdatePage.setDepreciationAmountInput('5'),
      assetDepreciationUpdatePage.setDepreciationDateInput('2000-12-31'),
      assetDepreciationUpdatePage.setCategoryIdInput('5'),
      assetDepreciationUpdatePage.setAssetItemIdInput('5')
    ]);
    expect(await assetDepreciationUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await assetDepreciationUpdatePage.getDepreciationAmountInput()).to.eq(
      '5',
      'Expected depreciationAmount value to be equals to 5'
    );
    expect(await assetDepreciationUpdatePage.getDepreciationDateInput()).to.eq(
      '2000-12-31',
      'Expected depreciationDate value to be equals to 2000-12-31'
    );
    expect(await assetDepreciationUpdatePage.getCategoryIdInput()).to.eq('5', 'Expected categoryId value to be equals to 5');
    expect(await assetDepreciationUpdatePage.getAssetItemIdInput()).to.eq('5', 'Expected assetItemId value to be equals to 5');
    await assetDepreciationUpdatePage.save();
    expect(await assetDepreciationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await assetDepreciationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AssetDepreciation', async () => {
    const nbButtonsBeforeDelete = await assetDepreciationComponentsPage.countDeleteButtons();
    await assetDepreciationComponentsPage.clickOnLastDeleteButton();

    assetDepreciationDeleteDialog = new AssetDepreciationDeleteDialog();
    expect(await assetDepreciationDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Asset Depreciation?');
    await assetDepreciationDeleteDialog.clickOnConfirmButton();

    expect(await assetDepreciationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
