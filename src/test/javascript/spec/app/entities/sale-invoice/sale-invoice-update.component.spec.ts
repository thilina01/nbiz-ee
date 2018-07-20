/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SaleInvoiceUpdateComponent } from 'app/entities/sale-invoice/sale-invoice-update.component';
import { SaleInvoiceService } from 'app/entities/sale-invoice/sale-invoice.service';
import { SaleInvoice } from 'app/shared/model/sale-invoice.model';
import { SaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';

describe('Component Tests', () => {
    describe('SaleInvoice Management Update Component', () => {
        let comp: SaleInvoiceUpdateComponent;
        let fixture: ComponentFixture<SaleInvoiceUpdateComponent>;
        let service: SaleInvoiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SaleInvoiceUpdateComponent]
            })
                .overrideTemplate(SaleInvoiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaleInvoiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleInvoiceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SaleInvoice(123);
                    entity.saleInvoiceItems = [ new SaleInvoiceItem() ];
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saleInvoice = entity;
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
                    const entity = new SaleInvoice();
                    entity.saleInvoiceItems = [ new SaleInvoiceItem() ];
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saleInvoice = entity;
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
