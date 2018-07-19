/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SaleInvoiceItemUpdateComponent } from 'app/entities/sale-invoice-item/sale-invoice-item-update.component';
import { SaleInvoiceItemService } from 'app/entities/sale-invoice-item/sale-invoice-item.service';
import { SaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';

describe('Component Tests', () => {
    describe('SaleInvoiceItem Management Update Component', () => {
        let comp: SaleInvoiceItemUpdateComponent;
        let fixture: ComponentFixture<SaleInvoiceItemUpdateComponent>;
        let service: SaleInvoiceItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SaleInvoiceItemUpdateComponent]
            })
                .overrideTemplate(SaleInvoiceItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaleInvoiceItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleInvoiceItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SaleInvoiceItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saleInvoiceItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SaleInvoiceItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saleInvoiceItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
