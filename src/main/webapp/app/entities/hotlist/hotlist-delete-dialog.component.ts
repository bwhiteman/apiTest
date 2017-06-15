import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Hotlist } from './hotlist.model';
import { HotlistPopupService } from './hotlist-popup.service';
import { HotlistService } from './hotlist.service';

@Component({
    selector: 'jhi-hotlist-delete-dialog',
    templateUrl: './hotlist-delete-dialog.component.html'
})
export class HotlistDeleteDialogComponent {

    hotlist: Hotlist;

    constructor(
        private hotlistService: HotlistService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hotlistService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hotlistListModification',
                content: 'Deleted an hotlist'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('apiTestApp.hotlist.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-hotlist-delete-popup',
    template: ''
})
export class HotlistDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hotlistPopupService: HotlistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.hotlistPopupService
                .open(HotlistDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
