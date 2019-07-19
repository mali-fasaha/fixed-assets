/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetItemUpdateComponent } from 'app/entities/fixed-asset-item/fixed-asset-item-update.component';
import { FixedAssetItemService } from 'app/entities/fixed-asset-item/fixed-asset-item.service';
import { FixedAssetItem } from 'app/shared/model/fixed-asset-item.model';

describe('Component Tests', () => {
  describe('FixedAssetItem Management Update Component', () => {
    let comp: FixedAssetItemUpdateComponent;
    let fixture: ComponentFixture<FixedAssetItemUpdateComponent>;
    let service: FixedAssetItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FixedAssetItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FixedAssetItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixedAssetItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FixedAssetItem(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FixedAssetItem();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
