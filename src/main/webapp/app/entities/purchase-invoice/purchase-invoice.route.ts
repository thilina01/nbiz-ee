import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PurchaseInvoice } from 'app/shared/model/purchase-invoice.model';
import { PurchaseInvoiceService } from './purchase-invoice.service';
import { PurchaseInvoiceComponent } from './purchase-invoice.component';
import { PurchaseInvoiceDetailComponent } from './purchase-invoice-detail.component';
import { PurchaseInvoiceUpdateComponent } from './purchase-invoice-update.component';
import { PurchaseInvoiceDeletePopupComponent } from './purchase-invoice-delete-dialog.component';
import { IPurchaseInvoice } from 'app/shared/model/purchase-invoice.model';

@Injectable({ providedIn: 'root' })
export class PurchaseInvoiceResolve implements Resolve<IPurchaseInvoice> {
    constructor(private service: PurchaseInvoiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((purchaseInvoice: HttpResponse<PurchaseInvoice>) => purchaseInvoice.body));
        }
        return of(new PurchaseInvoice());
    }
}

export const purchaseInvoiceRoute: Routes = [
    {
        path: 'purchase-invoice',
        component: PurchaseInvoiceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PurchaseInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-invoice/:id/view',
        component: PurchaseInvoiceDetailComponent,
        resolve: {
            purchaseInvoice: PurchaseInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-invoice/new',
        component: PurchaseInvoiceUpdateComponent,
        resolve: {
            purchaseInvoice: PurchaseInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-invoice/:id/edit',
        component: PurchaseInvoiceUpdateComponent,
        resolve: {
            purchaseInvoice: PurchaseInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseInvoices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseInvoicePopupRoute: Routes = [
    {
        path: 'purchase-invoice/:id/delete',
        component: PurchaseInvoiceDeletePopupComponent,
        resolve: {
            purchaseInvoice: PurchaseInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseInvoices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
