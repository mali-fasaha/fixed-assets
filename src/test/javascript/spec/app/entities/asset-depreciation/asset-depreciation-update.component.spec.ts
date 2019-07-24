/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetDepreciationUpdateComponent } from 'app/entities/asset-depreciation/asset-depreciation-update.component';
import { AssetDepreciationService } from 'app/entities/asset-depreciation/asset-depreciation.service';
import { AssetDepreciation } from 'app/shared/model/asset-depreciation.model';

describe('Component Tests', () => {
  describe('AssetDepreciation Management Update Component', () => {
    let comp: AssetDepreciationUpdateComponent;
    let fixture: ComponentFixture<AssetDepreciationUpdateComponent>;
    let service: AssetDepreciationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetDepreciationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssetDepreciationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssetDepreciationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetDepreciationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssetDepreciation(123);
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
        const entity = new AssetDepreciation();
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
